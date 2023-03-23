<script setup lang="ts">
import Vue3Autocounter from 'vue3-autocounter';
const props = defineProps({
  cntr: Number
})
</script>

<template>
  <span id="cntr" class='box'>
    <vue3-autocounter
        ref='counter'
        :startAmount='0'
        :endAmount='props.cntr'
        :duration='1'
        prefix=''
        suffix=''
        separator=','
        decimalSeparator='.'
        :decimals='0'
        :autoinit='true'/>
  </span>
</template>
