import React, { useState, useEffect } from 'react'
import Card from './Card'
import UserService from '../services/UserService'
import { toast } from 'react-toastify'
import { Navigate } from 'react-router-dom'
import Header from './Header'

function Creator() {
  const [blogs, setBlogs] = useState(null)
  const [redirect, setRedirect] = useState(null);

  const fetchData = async () => {
    try {
        const token = localStorage.getItem('token');
        const res = await UserService.getBlogsByCreator(token);
        const data = await res.json();
        console.log(data);
        setBlogs(data.allBlogsByCreator);
    } catch (err) {
        console.error("Error fetching data:", err);
    }
  }

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      toast.error('You need to login first');
      setRedirect('/login');
    }
    else if (!UserService.isCreator()) {
      toast.error('You are not a Creator');
      setRedirect('/');
    }

    fetchData();
  }, []);

  if (redirect) {
    return <Navigate to={redirect} />;
  }

  return (
    <>
      <Header/>
      <div className="flex flex-col items-center min-h-[calc(100vh-4rem)] bg-gray-100 box-border p-3">
          <div className='w-full flex justify-center mt-10'>
            <div className='grid sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 w-full gap-4 md:mx-20 lg:mx-40 mx-5'>
              {blogs && blogs.map((blog) => (<Card key={blog.id} blogId={blog.id} title={blog.title} date={blog.date} content={blog.content} />))}
            </div>
          </div>
      </div>
    </>
  )
}

export default Creator