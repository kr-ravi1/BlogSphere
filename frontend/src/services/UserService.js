import { toast } from 'react-toastify';
class UserService {
    static BASE_URL = "http://localhost:8080";

    static async login(userData) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/auth/login`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"  //  Set correct headers
                },
                body: JSON.stringify(userData)
            })
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async register(userData) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/auth/register`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"  //  Set correct headers
                },
                body: JSON.stringify(userData)  //  Convert object to JSON
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async createBlog(articleData, token) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/creator/blog/add`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(articleData)  //  Convert object to JSON
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async updateBlog(blogId, articleData, token) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/creator/blog/edit/${blogId}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(articleData)  //  Convert object to JSON
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async getBlogs(token) {
        try {
            const response = await fetch(`${UserService.BASE_URL}/creatoruser`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async getBlogsByCreator(token) {
        try {
            const response = await fetch(`${UserService.BASE_URL}/creator`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async getBlogById(token, blogId) {
        try {
            const response = await fetch(`${UserService.BASE_URL}/blog/${blogId}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async deleteBlog(token, blogId) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/creator/blog/delete/${blogId}`, {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async getAllUsers(token) {
        try {
            const response = await fetch(`${UserService.BASE_URL}/admin/allUsers`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                },
            });

            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async addSubscriber(token, email) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/subscriber/add/${email}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async removeSubscriber(token, email) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/subscriber/remove/${email}`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    static async getSubscriberStatus(token, email) {
        try{
            const response = await fetch(`${UserService.BASE_URL}/subscriber/status/${email}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response;
        }
        catch(err) {
            throw err;
        }
    }

    /**AUTHENTICATION CHECKER */
    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
        localStorage.removeItem('email')
        localStorage.removeItem('subscribed')
    }

    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }

    static isCreator(){
        const role = localStorage.getItem('role')
        return role === 'CREATOR';
    }

    static isAdmin(){
        const role = localStorage.getItem('role')
        return role === 'ADMIN';
    }

    static isUser(){
        const role = localStorage.getItem('role')
        return role === 'USER'
    }

    static creatorOnly(){
        return this.isAuthenticated() && this.isCreator();
    }

    static isSubscribed() {
        return localStorage.getItem('subscribed') === "true";
    }
}

export default UserService;