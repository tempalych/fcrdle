<script setup lang="ts">
import { store } from '@/store';
import {computed} from "vue";
import CntrCap from './AnimatedCounterCap.vue'

const winClub = computed(() => {return store.state.winClub})
const guessList = computed(() => {return store.state.guessList})
const puzzleDate = computed(() => {return store.state.puzzleDate})

function getDistancePrc(distance:Number) {
  if (distance < 500) {
    return "🟩🟩🟩🟩🟩⬜"
  } else if (distance < 1100) {
    return "🟩🟩🟩🟩⬜️⬜"
  } else if (distance < 2200) {
    return "🟩🟩🟩⬜⬜️⬜"
  } else if (distance < 3400) {
    return "🟩🟩⬜️⬜⬜️⬜"
  } else {
    return "🟩⬜⬜⬜⬜️⬜"
  }
}

function getLeagueIsCorrect(leagueIsCorrect:Boolean) {
  if (leagueIsCorrect) {
    return "🏆";
  } else {
    return "";
  }
}

function dissAlert(msg:String ,duration:Number) {
  var el = document.createElement("div");
  el.setAttribute("style","position:absolute;top:1%;left:30%;background-color:#00bd7e;");
  el.innerHTML = msg;
  setTimeout(function(){
    el.parentNode.removeChild(el);
  }, duration);
  document.body.appendChild(el);
}

function share() {
  dissAlert("Copied to clipboard", 3000);
  const element = document.getElementById("guessresult")
  var textToCopy = element.textContent
  if (!navigator.clipboard){
    const storage = document.createElement('textarea');
    storage.value = textToCopy;
    console.log("storage", storage.value)
    element.appendChild(storage);
    storage.select();
    storage.setSelectionRange(0, 99999);
    document.execCommand('copy');
    element.removeChild(storage);
  } else{
    navigator.clipboard.writeText(textToCopy)
  }
}
</script>

<template>
  <div>
    <div class="guess-name">{{ winClub.name }}</div>
    <div class="guess-second-str">🏆&nbsp{{ winClub.league }}</div>
    <div class="guess-second-str">🏟&nbsp{{ winClub.stadiumName }},
       {{winClub.city}},
      👥&nbsp<CntrCap :cntr="winClub.stadiumCapacity"/></div>
    <div class="guess-second-str"><button @click="share()">Share result...</button></div>

    <div id="guessresult" class="guess-result">
      <div class = "guess-second-str">#Fcrdle {{puzzleDate }} {{"\n"}}</div>
      <div class = "guess-second-str">🟩🟩🟩🟩🟩🟩🎉{{"\n"}}</div>
      <div class = "guess-container" v-for="guess in guessList" :key="guess.id">
        <div class="guess-second-str" :id="guess.id">
          {{ getDistancePrc(guess.howClose) }}{{ guess.direction }}{{getLeagueIsCorrect(guess.leagueIsCorrect)}}{{"\n"}}
        </div>
      </div>
      <div class = "guess-second-str">https://mtema.site</div>
    </div>
  </div>
</template>

<style>
.guess-first-str {
  text-align: center;
}

.guess-second-str {
  text-align: center;
}

.guess-last-str {
  text-align: center;
  margin-bottom: 2em;
}

.guess-name {
  text-align: center;
  font-size: x-large;
}

.guess-result {
  display:none;
}

</style>