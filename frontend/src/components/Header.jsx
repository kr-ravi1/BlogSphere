import React, { useEffect, useState } from 'react'
import { GiHamburgerMenu } from "react-icons/gi";
import { Link } from 'react-router-dom';
import UserService from '../services/UserService.js'

function Header() {

  const [showHamMenu, setShowHamMenu] = useState(false);
  const [isToken, setIsToken] = useState(localStorage.getItem('token'));
  const [isAdmin, setIsAdmin] = useState(UserService.adminOnly());

  useEffect(() => {
    setIsAdmin(UserService.adminOnly());
    setIsToken(localStorage.getItem('token'))
  }, []);

  return (
    <>
      <header className='h-15 bg-gray-100 flex justify-center items-center border-b border-gray-300'>
        <div className='h-15 mx-[10%] w-full flex justify-between items-center'>
          <h1 className='text-3xl font-bold'>BlogSphere</h1>
          <nav className='hidden md:inline-block'>
            <ul className='flex gap-7'>
              {isToken && <li className='text-md font-semibold'><Link to='/'>Home</Link></li>}
              {isAdmin && <li className='text-md font-semibold'><Link to='/creator'>My Blogs</Link></li>}
              {isAdmin && <li className='text-md font-semibold'><Link to='/blog/add'>Add Blogs</Link></li>}
              {!isToken && <li className='text-md font-semibold'><Link to='/login'>Login</Link></li>}
              {!isToken && <li className='text-md font-semibold'><Link to='/register'>SignUp</Link></li>}
              {isToken && <li className='text-md font-semibold'><Link to='/logout'>Logout</Link></li>}
            </ul>
          </nav>
          <nav className={` ${showHamMenu ? 'absolute md:hidden top-15 left-0 w-full bg-white shadow-md' : 'hidden'}`}>
            <ul className='flex flex-col gap-2 items-center'>
              {isToken && <li className='text-md font-semibold'><Link to='/'>Home</Link></li>}
              {isAdmin && <li className='text-md font-semibold'><Link to='/creator'>My Blogs</Link></li>}
              {isAdmin && <li className='text-md font-semibold'><Link to='/blog/add'>Add Blogs</Link></li>}
              {!isToken && <li className='text-md font-semibold'><Link to='/login'>Login</Link></li>}
              {!isToken && <li className='text-md font-semibold'><Link to='/register'>SignUp</Link></li>}
              {isToken && <li className='text-md font-semibold'><Link to='/logout'>Logout</Link></li>}
            </ul>
          </nav>
          <GiHamburgerMenu className='text-xl md:hidden' onClick={() => setShowHamMenu(!showHamMenu)} />
        </div>
      </header>
    </>
  )
}

export default Header