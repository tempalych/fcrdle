<script setup lang="ts">
import GuessList from "./GuessList.vue";
import { store } from '@/store';
import {computed, onMounted, ref} from "vue";
import Win from './Win.vue'

const puzzleDate = computed(() => {return store.state.puzzleDate})
const gameFinished = computed(() => {return store.state.gameFinished})
const clubNames = store.state.clubList
let userId = getUserId();

function getUserId() {
  if (localStorage.hasOwnProperty("userId")) {
    return localStorage.getItem("userId")
  }
  else {
    return localStorage.setItem("userId", crypto.randomUUID())
  }
}


const loadPuzzle = async () => {
  const response = await fetch("/api/v1/dailypuzzle", {
  // const response = await fetch("http://localhost:8080/api/v1/dailypuzzle", {
    method: "PUT",
    headers: {'Content-type': 'application/json'},
    body: JSON.stringify({
      "date": new Date().toISOString().slice(0, 10),
      "userId": userId
    })
  });
  const puzzleResponse = await response.json()
  if (puzzleResponse.date != ref(puzzleDate).value) {
    store.commit("dropStore")
  }
  store.commit('setPuzzleDate', puzzleResponse.date)
}

onMounted (() => {
  userId = getUserId()
  loadPuzzle();
})

const guess = async () => {
  const guessInput = <HTMLInputElement>document.getElementById("typeahead_id");
  if (clubNames.includes(guessInput.value)) {
    const response = await fetch("/api/v1/guess", {
    // const response = await fetch("http://localhost:8080/api/v1/guess", {
      method: "PUT",
      headers: {'Content-type': 'application/json'},
      body: JSON.stringify({
        "date": new Date().toISOString().slice(0, 10),
        "footballClubId": guessInput.value,
        "userId": userId
      })
    });
    const guessResponse = await response.json();
    if (guessResponse.correct == true) {
      store.commit("setWinClub", {
        name: guessInput.value,
        stadiumName: guessResponse.stadiumName,
        city: guessResponse.city,
        league: guessResponse.league,
        stadiumCapacity: guessResponse.stadiumCapacity
      })
      store.commit('setGameFinished', true)
      guessInput.value = "";
    } else {
      store.commit('addGuess', {
        name: guessInput.value,
        direction: guessResponse.direction,
        howClose: guessResponse.howClose,
        stadiumCapacity: guessResponse.stadiumCapacity,
        guessCapacityIsLess: guessResponse.guessCapacityIsLess,
        league: guessResponse.league,
        leagueIsCorrect: guessResponse.leagueIsCorrect
      })
    }
  }
}
</script>

<template>
  <div class="greetings">
    <h1><span class="fc">FC</span>RLDE</h1>
    <h3><Win v-if="gameFinished"/></h3>
    <div v-if="!gameFinished" class="guess">
      <vue3-simple-typeahead
        id="typeahead_id"
        placeholder="Football Club name..."
        :items="clubNames"
        :minInputLength="1"
        :disabled='gameFinished'></vue3-simple-typeahead>
    </div>
    <div v-if="!gameFinished" class="guess-btn"><input type="submit" :disabled='gameFinished' @click="guess()" value="Guess ⚽️" /></div>
    <GuessList/>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  top: -10px;
}

span.fc {
  color: #00bd7e;
}

h3 {
  font-size: 1.2rem;
}

.guess {
  text-align: center;
}

.guess-btn {
  text-align: center;
  margin-bottom: 1em;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
