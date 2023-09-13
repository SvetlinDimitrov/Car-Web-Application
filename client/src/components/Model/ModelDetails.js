import { useState, useEffect , useContext , useRef} from "react";
import { useParams, Link , useNavigate} from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";

import { getModelById } from "../../utils/ModelService";

const ModelDetails = () => {
    const getData = useRef(false);
    const navigate = useNavigate();
    const [ model , setModel ] = useState({});
    const { id } = useParams();
    const { user , logout } = useContext(AuthContext);

    useEffect(() => {
        const fetchData = async () =>{
            const response = await getModelById(user , id);

            if(!response.ok){
                
                if(response === 401){
                    logout();
                    navigate('/login');
                }else{
                    navigate('/error');
                }
            }else{
                const data = await response.json();
                setModel(data[0]);
            }
        }
        if(!getData.current){
            getData.current = true;
            fetchData();
        }
    },[id , logout , navigate ,user])

  return (
    <div className="container-fluid">
      <h2 className="text-center text-white mt-5">Model Details</h2>
      {model.id && (
        <div className="offers row mx-auto d-flex flex-row justify-content-center">
          <div className="offer card col-sm-2 col-md-3  col-lg-4 m-1 p-0">
            <div className="card-body pb-1">
              <h5 className="card-title">
                {model.brand.name} {model.name}
              </h5>
            </div>
            <ul className="offer-details list-group list-group-flush">
              <li className="list-group-item">
                <div className="card-text">
                  <span>• Category : {model.category}</span>
                </div>
                <div className="card-text">
                  <span>• Created : {model.created}</span>
                </div>
                <div className="card-text">
                  <span>• Generation : {model.generation}</span>
                </div>
                <div className="card-text">
                  <span>• Sells available : {model.offerList.length}</span>
                </div>
              </li>
            </ul>
            {user.authorities.some((r) => r === "ROLE_ADMIN") && (
              <div className="card-body">
                <Link className="card-link" to={`/models/update/${model.id}`}>
                  Update
                </Link>
                <Link className="card-link" to={`/models/delete/${model.id}`}>
                  Delete
                </Link>
              </div>
            )}
          </div>
          <div className="offer card col-sm-2 col-md-3  col-lg-4 m-1 p-0">
            <img src={model.imageUrl} className="card-img-top" alt="Car" />
          </div>
        </div>
      )}
    </div>
  );
};

export default ModelDetails;
