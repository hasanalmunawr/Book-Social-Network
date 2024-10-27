import './assets/main.css'
import './assets/tailwind.css';
import {library} from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from '@fortawesome/free-solid-svg-icons';
import { far } from '@fortawesome/free-regular-svg-icons';

import { createApp } from 'vue'

import App from './App.vue'
import router from './router'


library.add(fas)
library.add(far);
const app = createApp(App)

app.use(router)
app.component('font-awesome-icon', FontAwesomeIcon);
app.mount('#app')
