import React, { useEffect, useState } from 'react';
import { GiHamburgerMenu } from "react-icons/gi";
import { NavLink } from 'react-router-dom';
import UserService from '../services/UserService.js';
import { toast } from 'react-toastify';
import ErrorService from '../services/ErrorService.js';

function Header() {
  const [showHamMenu, setShowHamMenu] = useState(false);
  const [isToken, setIsToken] = useState(localStorage.getItem('token'));
  const [isCreator, setIsCreator] = useState(UserService.creatorOnly());
  const [isAdmin, setIsAdmin] = useState(UserService.isAdmin());
  const [isSubscribed, setIsSubscribed] = useState(UserService.isSubscribed());
  const token = localStorage.getItem('token');
  const email = localStorage.getItem('email');

  const handleSubscriber = async () => {
    
    let res;
    if(isSubscribed) {
      res = await UserService.removeSubscriber(token, email);
    }
    else {
      res = await UserService.addSubscriber(token, email);
    }

    const data = await res.json();
    localStorage.setItem('subscribed', data.subscribed);
    toast.success(data.message);
    setIsSubscribed((prev) => !prev)
  };

  // Fetch updated subscription status when token changes
  useEffect( () => {
    const fetchStatus = async () => {
      try{
        if(isToken && !isAdmin) {
          const res = await UserService.getSubscriberStatus(token, email);
          const data = await res.json();
          localStorage.setItem('subscribed', data.subscribed)
          setIsSubscribed(data.subscribed);
        }
      }
      catch(error) {
        toast.error(error.message);
      }
    }
    fetchStatus();
    setIsCreator(UserService.creatorOnly());
    setIsToken(localStorage.getItem('token'));
    setIsAdmin(UserService.isAdmin());
  }, []);

  

  return (
    <>
      <header className='h-15 bg-gray-100 flex justify-center items-center border-b border-gray-300'>
        <div className='h-15 mx-[10%] w-full flex justify-between items-center'>
          <h1 className='text-3xl font-bold'>BlogSphere</h1>
          
          {isToken && !isAdmin && (
            <button 
              onClick={handleSubscriber} 
              className={`px-4 py-2 rounded ${isSubscribed ? "bg-green-700" : "bg-blue-700"} text-white font-semibold`}
            >
              {isSubscribed ? "Subscribed" : "Subscribe"}
            </button>
          )}

          <nav className='hidden md:inline-block'>
            <ul className='flex gap-7'>
              {isToken && <li className='text-md font-semibold'><NavLink to='/' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Home</NavLink></li>}
              {isCreator && <li className='text-md font-semibold'><NavLink to='/creator' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>My Blogs</NavLink></li>}
              {isCreator && <li className='text-md font-semibold'><NavLink to='/blog/add' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Add Blogs</NavLink></li>}
              {isAdmin && <li className='text-md font-semibold'><NavLink to='/admin/allUsers' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>All Users</NavLink></li>}
              {!isToken && <li className='text-md font-semibold'><NavLink to='/login' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Login</NavLink></li>}
              {!isToken && <li className='text-md font-semibold'><NavLink to='/register' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>SignUp</NavLink></li>}
              {isToken && <li className='text-md font-semibold'><NavLink to='/logout' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Logout</NavLink></li>}
            </ul>
          </nav>

          <GiHamburgerMenu className='text-xl md:hidden' onClick={() => setShowHamMenu(!showHamMenu)} />

          {/* Mobile Menu */}
          <nav className={` ${showHamMenu ? 'absolute md:hidden top-15 left-0 w-full bg-white shadow-md z-1000' : 'hidden'}`}>
            <ul className='flex flex-col gap-2 items-center'>
              {isToken && <li className='text-md font-semibold'><NavLink to='/' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Home</NavLink></li>}
              {isCreator && <li className='text-md font-semibold'><NavLink to='/creator' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>My Blogs</NavLink></li>}
              {isCreator && <li className='text-md font-semibold'><NavLink to='/blog/add' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Add Blogs</NavLink></li>}
              {isAdmin && <li className='text-md font-semibold'><NavLink to='/admin/allUsers' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>All Users</NavLink></li>}
              {!isToken && <li className='text-md font-semibold'><NavLink to='/login' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Login</NavLink></li>}
              {!isToken && <li className='text-md font-semibold'><NavLink to='/register' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>SignUp</NavLink></li>}
              {isToken && <li className='text-md font-semibold'><NavLink to='/logout' className={({ isActive }) => isActive ? "border-b-3 border-blue-700" : ""}>Logout</NavLink></li>}
            </ul>
          </nav>
        </div>
      </header>
    </>
  );
}

export default Header;
