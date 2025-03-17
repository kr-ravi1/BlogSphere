import { toast } from 'react-toastify';
class ErrorService  {
    static async getErrorMessage(status, error) {
        if(status === 400) {
            console.log("Error:", error.message);
            toast.error("Bad request: "+ error.message);
        }
        else if(status === 401) {
            toast.error('Unauthorized: '+ error.message);
        }
        else if(status === 403) {
            toast.error('Forbidden: '+ error.message);
        }
        else if(status === 404) {
            toast.error('Resource not found: '+ error.message);
        }
        else if(status === 500) {
            toast.error('Internal server error: '+ error.message);
        }
        else {
            toast.error('Unknown Error: '+ error.message);
        }
    }
}

export default ErrorService;

