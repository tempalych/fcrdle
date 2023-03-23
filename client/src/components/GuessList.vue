<script setup lang="ts">
import { store } from '@/store';
import Cntr from './AnimatedCounter.vue'
import CntrCap from './AnimatedCounterCap.vue'
import {computed} from "vue";

const guessList = computed(() => {return store.state.guessList})

</script>

<template>
  <div class = "guess-container" v-for="guess in guessList" :key="guess.id">
    <div class="guess-first-str">
      <span class = "guess-name" :id="guess.id">{{guess.name}}&nbsp</span>
    </div>


    <div class="guess-second-str">
      <span class = "guess-howClose" :id="guess.id">{{guess.league}}&nbsp</span>
      <span v-if="guess.leagueIsCorrect===true" class = "guess-capacityIsLess" :id="guess.id">✅</span>
      <span v-if="guess.leagueIsCorrect===false" class = "guess-capacityIsLess" :id="guess.id">❌️</span>
    </div>

    <div class="guess-second-str">
      <span class = "guess-howClose" :id="guess.id">📍&nbsp<Cntr :id="guess.id" :cntr="guess.howClose"/>&nbsp</span>
      <span class = "guess-direction" :id="guess.id">{{guess.direction}}</span>
    </div>

    <div class="guess-last-str">
      <span class = "guess-capacity" :id="guess.id">👥&nbsp<CntrCap :id="guess.id" :cntr="guess.stadiumCapacity"/>&nbsp</span>
      <span v-if="guess.guessCapacityIsLess===true" class = "guess-capacityIsLess" :id="guess.id">⬆️</span>
      <span v-if="guess.guessCapacityIsLess===false" class = "guess-capacityIsLess" :id="guess.id">⬇️</span>
    </div>
  </div>
</template>


<style scoped>
.guess-first-str {
  text-align: center;
}

.guess-second-str {
  text-align: center;
  color: darkgray;
}

.guess-last-str {
  text-align: center;
  margin-bottom: 2em;
  color: darkgray;
}

.guess-name {
  text-align: center;
  font-size: large;
}

.guess-league {
  text-align: center;
  height: 30px;
  filter: grayscale(100%) opacity(45%);
}

.guess-league-correct {
  text-align: center;
  height: 30px;
}

</style>
