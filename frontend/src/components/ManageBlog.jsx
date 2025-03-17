import React, { useEffect, useState } from 'react';
import { useParams, Navigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import UserService from '../services/UserService';
import ErrorService from '../services/ErrorService';
import Header from './Header';

function ManageBlog() {
    const { blogId } = useParams();
    const [redirect, setRedirect] = useState(null);
    const token = localStorage.getItem('token');
    const today = new Date().toISOString().split('T')[0];
    const [articleData, setArticleData] = useState({
        title: '',
        date: today,
        content: ''
    });

    useEffect(() => {
        if (!localStorage.getItem('token')) {
            toast.error('You need to login first');
            setRedirect('/login');
        } else if (!UserService.isAdmin()) {
            toast.error('You are not an Admin');
            setRedirect('/');
        } else if (blogId) {
            fetchBlogDetails();
        }
    }, [blogId]);

    const fetchBlogDetails = async () => {
        try {
            const response = await UserService.getBlogById(token, blogId);
            if (response.ok) {
                const data = await response.json();
                // console.log(data);
                const formattedDate = convertDateFormat(data.date);
                setArticleData({
                    title: data.title,
                    date: formattedDate,
                    content: data.content
                });
            } else {
                ErrorService.getErrorMessage(response.status, await response.json());
            }
        } catch (error) {
            toast.error("Failed to fetch blog details");
        }
    };
    
    const convertDateFormat = (dateStr) => {
        const [day, month, year] = dateStr.split('-');
        return `${year}-${month}-${day}`;
    };
    

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setArticleData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let response;
            if (blogId) {
                response = await UserService.updateBlog(blogId, articleData, token);
            } else {
                response = await UserService.createBlog(articleData, token);
            }
            console.log(response);
            const data = await response.json();
            if(response.status === 403) {
                ErrorService.getErrorMessage(response.status, data)
            }
            if (response.status === 201) {
                toast.success(data.message);
                setRedirect('/creator');
            } else {
                ErrorService.getErrorMessage(response.status, data);
            }
        } catch (error) {
            toast.error("Failed to save blog" + error);
        }
    };

    if (redirect) {
        return <Navigate to={redirect} />;
    }

    return (
        <>
            <Header />
            <div className='flex flex-col items-center min-h-[calc(100vh-4rem)] bg-gray-100 box-border p-3'>
                <div className="w-full max-w-xl p-4 bg-white border border-gray-200 rounded-lg shadow-sm sm:p-6 md:p-8">
                    <form className="space-y-6" onSubmit={handleSubmit}>
                        <h5 className="text-xl font-medium text-gray-900">{blogId ? 'Edit' : 'Add'} Blog</h5>
                        <div>
                            <label htmlFor="title" className="block mb-2 text-sm font-medium text-gray-900 pl-2">Title:</label>
                            <input type="text" name="title" value={articleData.title} onChange={handleInputChange} id="title" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Title goes here..." required />
                        </div>
                        <div>
                            <label htmlFor="date" className="block mb-2 text-sm font-medium text-gray-900 pl-2">Date:</label>
                            <input type="date" name="date" value={articleData.date} onChange={handleInputChange} id="date" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" required />
                        </div>
                        <div>
                            <label htmlFor="content" className="block mb-2 text-sm font-medium text-gray-900 pl-2">Content:</label>
                            <textarea name="content" value={articleData.content} onChange={handleInputChange} id="content" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Content goes here..." required />
                        </div>
                        <button type="submit" className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">{blogId ? 'Update' : 'Create'}</button>
                    </form>
                </div>
            </div>
        </>
    );
}

export default ManageBlog;
