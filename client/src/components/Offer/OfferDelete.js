import { useParams, useNavigate } from "react-router-dom";
import { useContext, useEffect , useRef} from "react";

import { deleteOffer } from "../../utils/OfferService";

import { AuthContext } from "../../contexts/UserAuth";

const OfferDelete = () => {
  const { id } = useParams();
  const hasRunRef = useRef(false);
  const { user ,logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    async function deleteData() {
      const response = await deleteOffer(id, user);

      if(!response.ok){
        if(response.status === 401){
          logout();
          navigate('/login');
        }else{
          navigate("/error");
        }
      }else{
        navigate("/offers");
      }  
    }
    if(!hasRunRef.current){
      hasRunRef.current = true;
      deleteData();

    }
   
  }, [id, user, navigate , logout]);
};

export default OfferDelete;
