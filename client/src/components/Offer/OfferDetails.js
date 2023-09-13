import { useContext, useEffect, useState } from "react";
import { useParams, Link , useNavigate} from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";
import { getOfferById } from "../../utils/OfferService";

const Details = () => {
  const navigate = useNavigate();
  const { user , logout } = useContext(AuthContext);
  const { id } = useParams();
  const [offer, setOffer] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const response = await getOfferById(id, user);

      if(!response.ok){
        if(response === 401){
          logout();
          navigate('/login');
        }else{
          navigate('/error')
        }
      }else{
        const data = await response.json();
        setOffer(data[0]);
      }  
    };
    fetchData();
  }, [id, user , logout , navigate]);

  return (
    <div className="container-fluid">
      <h2 className="text-center text-white mt-5">Details</h2>
      {offer.id && (
        <div className="offers row mx-auto d-flex flex-row justify-content-center">
          <div className="offer card col-sm-2 col-md-3  col-lg-4 m-1 p-0">
            <div className="card-body pb-1">
              <h5 className="card-title">
                {offer.year} {offer.brandName} {offer.model.name}
              </h5>
            </div>
            <ul className="offer-details list-group list-group-flush">
              <li className="list-group-item">
                <div className="card-text">
                  <span>• Mileage : {offer.mileage}</span>
                </div>
                <div className="card-text">
                  <span>• Year : {offer.year}</span>
                </div>
                <div className="card-text">
                  <span>• Price : {offer.price}</span>
                </div>
                <div className="card-text">
                  <span>• Engine type : {offer.engine}</span>
                </div>
                <div className="card-text">
                  <span>• Transmission type : {offer.transmission}</span>
                </div>
                <div className="card-text">
                  <span>• Offer created : {offer.created}</span>
                </div>
                <div className="card-text">
                  <span>• Offer modified : {offer.modified}</span>
                </div>
                <div className="card-text">
                  <span>
                    • Seller - {offer.seller.firstName} {offer.seller.lastName}
                  </span>
                </div>
              </li>
            </ul>
            {(user.id === offer.seller.id || user.authorities.some(r => r === 'ROLE_ADMIN')) && (
              <div className="card-body">
                <Link className="card-link" to={`/offers/update/${offer.id}`}>
                  Update
                </Link>
                <Link className="card-link" to={`/offers/delete/${offer.id}`}>
                  Delete
                </Link>
              </div>
            )}
          </div>
          <div className="offer card col-sm-2 col-md-3  col-lg-4 m-1 p-0">
            <img src={offer.imageUrl} className="card-img-top" alt="Car" />
          </div>
        </div>
      )}
    </div>
  );
};

export default Details;
