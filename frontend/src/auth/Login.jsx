import React, { useState } from 'react'
import { toast } from 'react-toastify';
import UserService from '../services/UserService';
import ErrorService from '../services/ErrorService';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import Header from '../components/Header';

function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  })

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await UserService.login(formData);
      setFormData({
        email: '',
        password: '',
      })
      const data = await res.json();
      console.log(data);
      if (res.status === 200) {
        localStorage.setItem('token', data.token)
        localStorage.setItem('role', data.user.role)
        localStorage.setItem('email', data.user.email)
        localStorage.setItem('subscribed', data.user.subscribed)
        toast.success(data.message);
        navigate('/');
      }
      else {
        ErrorService.getErrorMessage(res.status, data);
      }
    } catch (error) {
      if (error.message === "Failed to fetch") {
        toast.error("Failed to connect to server");
      }
      toast.error(error);
    }
  }


  return (
    <>
      <Header/>
      <div className="flex justify-center items-center min-h-[calc(100vh-4rem)] bg-gray-100">
        <div className="bg-white shadow-lg rounded-lg p-6 w-full max-w-md">
          <h2 className="text-2xl font-semibold text-center text-gray-800 mb-4">Login</h2>
          <form onSubmit={handleSubmit} className="space-y-4">

            {/* Email */}
            <div>
              <label className="px-1 block text-gray-700">Email:</label>
              <input
                type="email"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
                required
                className="w-full p-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            {/* Password */}
            <div>
              <label className="px-1 block text-gray-700">Password:</label>
              <input
                type="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                required
                className="w-full p-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>


            {/* Submit Button */}
            <button
              type="submit"
              className="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition-all"
            >
              Login
            </button>
            <div className="text-sm font-medium text-gray-500">
              Not registered? <Link to='/register' className="text-blue-700 hover:underline">Create account</Link>
            </div>
          </form>
        </div>
      </div>
    </>
  )
}

export default Login