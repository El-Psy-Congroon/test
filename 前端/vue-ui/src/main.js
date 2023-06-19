
import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import '/src/assets/css/global.css'
import router from "@/router"
import "./utils/permission"
import "./utils/request"

import * as ElementPlusIconsVue from '@element-plus/icons-vue'



const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router)
app.use(ElementPlus)
app.mount('#app')


