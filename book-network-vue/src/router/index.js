import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import DashboardView from "@/views/DashboardView.vue";
import MyBookView from "@/views/books/MyBookView.vue";
import BorrowedBookView from "@/views/books/BorrowedBookView.vue";
import NotFoundView from "../errors/NotFoundView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView
    },
    {
      path: '/my-books',
      name: 'my-books',
      component: MyBookView
    },
    {
      // path: '/my-books/:id',
      path: '/borrowed-book',
      name: 'borrowed-book',
      component: BorrowedBookView
    },
    {
      path: '/:catchAll(.*)',
      name: 'not-found',
      component: NotFoundView
    }
  ]
})

export default router
