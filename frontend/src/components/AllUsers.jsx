import React, { useState, useEffect } from 'react'
import UserService from '../services/UserService'
import { toast } from 'react-toastify'
import Header from './Header'

function AllUsers() {
    const [redirect, setRedirect] = useState(null);
    const [users, setUsers] = useState(null);

    const fetchData = async () => {
        try {
            const token = localStorage.getItem('token');
            const res = await UserService.getAllUsers(token);
            const data = await res.json();
            console.log(data);
            setUsers(data.allUsers);
        } catch (err) {
            console.error("Error fetching data:", err);
        }
    }

    useEffect(() => {
        if (!localStorage.getItem('token')) {
            toast.error('You need to login first');
            setRedirect('/login');
        }
        else if (!UserService.isAdmin()) {
            toast.error('You are not an Admin');
            setRedirect('/');
        }

        fetchData();
    }, []);


    return (
        <>
            <Header />
            <div className="flex flex-col items-center min-h-[calc(100vh-4rem)] bg-gray-100 box-border p-3">
                <div className='w-full flex justify-center mt-10'>

                    <div className="relative overflow-x-auto shadow-md sm:rounded-lg md:w-[50%] w-[75%]">
                        <table className="w-full text-sm text-left rtl:text-right text-gray-500">
                            <thead className="text-xs text-gray-700 uppercase">
                                <tr className="border-b border-gray-200"> 
                                    <th scope="col" className="px-6 py-3 bg-gray-50">
                                        Name
                                    </th>
                                    <th scope="col" className="px-6 py-3 bg-gray-100">
                                        City
                                    </th>
                                    <th scope="col" className="px-6 py-3 bg-gray-50">
                                        Role
                                    </th>
                                    <th scope="col" className="px-6 py-3 bg-gray-100">
                                        Subscribed
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                            {users && users.map((user) => (<tr className="border-b border-gray-200" key={user.id}>
                                    <th scope="row" className="px-6 py-2 font-medium text-gray-900 whitespace-nowrap bg-gray-50 flex flex-col">
                                        <span>{user.name}</span>
                                        <span className="text-gray-500">{user.email}</span>
                                    </th>
                                    <td className="px-6 py-2 bg-gray-100">
                                        {user.city}
                                    </td>
                                    <td className="px-6 py-2 bg-gray-50">
                                        {user.role}
                                    </td>
                                    <td className="px-6 py-4 bg-gray-100">
                                        {user.subscribed ? "YES" : "NO"}
                                    </td>
                                </tr>))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </>
    )
}

export default AllUsers