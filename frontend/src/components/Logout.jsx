import React, { useEffect } from 'react'
import UserService from '../services/UserService'
import { Navigate } from 'react-router-dom';

function Logout() {

    useEffect(() => {
        UserService.logout();
    }, [])

    return (
        <Navigate to='/login'/>
    )
}

export default Logout