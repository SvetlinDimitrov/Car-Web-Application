import { useEffect , useContext , useRef} from "react";
import { useParams , useNavigate} from "react-router-dom";

import { deleteBrand } from "../../utils/BrandService";

import { AuthContext } from "../../contexts/UserAuth";

const BrandDelete = () => {
    const hasUsedRef = useRef(false);
    const { id } = useParams();
    const { user , logout } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () =>{
            const response = await deleteBrand(user , id);

            if(!response.ok){
                if(response.status === 401){
                    logout();
                    navigate('/login');
                }else{
                    navigate('/error')
                }
            }else{
                navigate('/brands');
            }
        }
        if(!hasUsedRef.current){
            hasUsedRef.current = true;
            fetchData();
        }
        
    },[id,logout,navigate,user])
}

export default BrandDelete;