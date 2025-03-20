import React, { useState, useEffect } from 'react'
import Header from './Header';
import UserService from '../services/UserService';
import { useNavigate, useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import ErrorService from '../services/ErrorService';

function Blog() {
    const navigate = useNavigate();
    const { blogId } = useParams();
    const token = localStorage.getItem('token');
    const [isAdmin, setIsAdmin] = useState(UserService.isAdmin());
    const [blog, setBlog] = useState({
        blogId: '',
        title: '',
        date: '',
        content: '',
        isOwner: false
    })

    const fetchData = async () => {
        try {
            const res = await UserService.getBlogById(token, blogId);

            const data = await res.json();
            console.log(data);
            setBlog({
                blogId: data.blogId,
                title: data.title,
                date: data.date,
                content: data.content,
                isOwner: data.owner
            })
            // console.log(blog.isOwner);
        } catch (err) {
            console.error("Error fetching data:", err);
        }
    };

    useEffect(() => {
        setIsAdmin(UserService.isAdmin());
        fetchData();
    }, []);

    const handleEdit = () => {
        navigate(`/blog/edit/${blog.blogId}`)
    }

    const handleDelete = async () => {
        try {
            const res = await UserService.deleteBlog(token, blogId);
            console.log(res.status);
            const data = await res.json();
            if (res.status === 200) {
                toast.success(data.message);
                navigate('/creator');
            } else {
                ErrorService.getErrorMessage(res.status, data);
            }
        }
        catch(error) {
            toast.error("Unfortunately blog is not Deleted." + error);
        }
    }

    return (
        <>
            <Header />
            <div className='flex flex-col items-center min-h-[calc(100vh-4rem)] bg-gray-100 box-border p-3'>
                <div className="block p-6 bg-white border border-gray-200 rounded-lg shadow-sm w-[75vw] md:w-[50vw]">
                    <div className='flex flex-row justify-between'>
                        <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">{blog.title}</h5>
                        <div className='flex flex-row gap-3 justify-center items-center'>
                            {(blog.isOwner || isAdmin) && <span title='Edit' className='cursor-pointer' onClick={handleEdit}><svg className="w-6 h-6 text-gray-800" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m14.304 4.844 2.852 2.852M7 7H4a1 1 0 0 0-1 1v10a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-4.5m2.409-9.91a2.017 2.017 0 0 1 0 2.853l-6.844 6.844L8 14l.713-3.565 6.844-6.844a2.015 2.015 0 0 1 2.852 0Z" />
                            </svg>
                            </span>}
                            {(blog.isOwner || isAdmin) && <span title='Delete' className='cursor-pointer' onClick={handleDelete}><svg className="w-6 h-6 text-gray-800" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 7h14m-9 3v8m4-8v8M10 3h4a1 1 0 0 1 1 1v3H9V4a1 1 0 0 1 1-1ZM6 7h12v13a1 1 0 0 1-1 1H7a1 1 0 0 1-1-1V7Z" />
                            </svg>
                            </span>}
                        </div>
                    </div>
                    <h6 className='text-base text-gray-500'>{blog.date}</h6>
                    <p className="font-normal text-gray-700 pt-8">{blog.content}</p>
                </div>
            </div>
        </>
    )
}

export default Blog