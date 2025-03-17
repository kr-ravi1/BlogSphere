import React from 'react'
import { Link } from 'react-router-dom';

function Card({blogId, title, date, content}) {
    return (


        <div className=" p-6 bg-white border border-gray-200 rounded-lg shadow-sm flex flex-col">
            <div className='flex flex-row justify-between'>
                <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900">{title}</h5>
                <p className='text-gray-500 text-sm flex items-center'>{date}</p>
            </div>
            <div className=''><p className="mb-3 font-normal text-gray-700">{content}</p></div>
            <div className=' mt-auto'>
            <Link to={`/blog/${blogId}`} className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800">
                Read more
                <svg className="rtl:rotate-180 w-3.5 h-3.5 ms-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M1 5h12m0 0L9 1m4 4L9 9" />
                </svg>
            </Link>
            </div>
        </div>

    )
}

export default Card