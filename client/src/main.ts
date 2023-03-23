import { createApp } from "vue"
import App from './App.vue'
import SimpleTypeahead from 'vue3-simple-typeahead';

import './assets/main.css'
import {store} from "@/store";

const app = createApp(App);

app.use(SimpleTypeahead)
app.use(store)
app.mount("#app")
