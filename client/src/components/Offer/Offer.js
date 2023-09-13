import { useEffect, useContext  , useState} from "react";
import { Link , useNavigate} from "react-router-dom";

import { getAllOffers } from "../../utils/OfferService";
import { AuthContext } from "../../contexts/UserAuth";

const Offer = () => {
  const navigate = useNavigate();
  const [ offers , initOffers ] = useState([])
  const { user , logout } = useContext(AuthContext);

  useEffect(() => {
    const getData = async () => {
      const response = await getAllOffers(user);
      if(response.status === 401){
        logout();
        navigate('/login');
      }else{
        const data = await response.json();
        initOffers(data);
      }
      
    };
    getData();
  }, [user , logout , navigate]);

  return (
    <div className="container-fluid">
      <h2 className="text-center text-white mt-5">All Offers</h2>
      <div className="offers row mx-auto d-flex flex-row justify-content-center">
        {offers.map((offer) => (
          <div
            key={offer.id}
            className="offer card col-sm-6 col-md-3  col-lg-2 m-1 p-0"
          >
            <div className="card-img-top-wrapper">
              <img className="card-img-top" src={offer.imageUrl} alt="Car" />
            </div>
            <div className="card-body pb-1">
              <h5 className="card-title">{offer.year} {offer.brandName} {offer.model.name}</h5>
            </div>
            <ul className="offer-details list-group list-group-flush">
              <li className="list-group-item">
                <div className="card-text">
                  <span>• Mileage : {offer.mileage}</span>
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
              </li>
            </ul>
            <div className="card-body">
              <Link className="card-link" to={`/offers/details/${offer.id}`}>
                Details
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Offer;
