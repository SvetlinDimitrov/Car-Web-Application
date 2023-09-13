import { useParams , useNavigate } from "react-router-dom";
import { useEffect , useRef , useContext } from "react";

import { AuthContext } from '../../contexts/UserAuth';

import { deleteModel } from "../../utils/ModelService";


const ModelDelete = () => {
    const { id } = useParams();
    const { user , logout} = useContext(AuthContext);
    const navigate = useNavigate();
    const deleted = useRef(false);

    useEffect(() => {
        const fetchData = async () => {
            const response = await deleteModel(id,user);
            if(!response.ok){
                if(response.status === 401){
                    logout();
                    navigate('/login');
                }else{
                    navigate('/error');
                }
            }else{
                navigate('/models')
            }
        }
        if(!deleted.current){
            deleted.current = true;
            fetchData();
        } 
    }, [id,user,logout,navigate])
}

export default ModelDelete;