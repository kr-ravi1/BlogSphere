import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { createBrowserRouter, createRoutesFromElements, RouterProvider, Route } from 'react-router-dom'
import Layout from './components/Layout';
import Home from './components/Home';
import Creator from './components/Creator';
import Register from './auth/Register';
import {ToastContainer, Bounce } from 'react-toastify';
import Login from './auth/Login';
import Blog from './components/Blog';
import ManageBlog from './components/ManageBlog';
import Logout from './components/Logout';
import Error from './components/Error';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Layout/>}> 
      <Route path="" element={<Home />} />
      <Route path="register" element={<Register />} />
      <Route path="login" element={<Login />} />
      <Route path="logout" element={<Logout/>} />
      <Route path="blog/:blogId" element={<Blog />} /> 
      <Route path="creator" element={<Creator />} />
      <Route path="blog/add" element={<ManageBlog/>} />
      <Route path="blog/edit/:blogId" element={<ManageBlog/>} />
      <Route path="*" element={<Error/>} />
    </Route>
  )
)

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router} />
    <ToastContainer
      position="top-right"
      autoClose={3000}
      hideProgressBar={false}
      newestOnTop={false}
      closeOnClick={false}
      rtl={false}
      pauseOnFocusLoss
      draggable
      pauseOnHover
      theme="light"
      transition={Bounce}
      />
  </StrictMode>,
)
