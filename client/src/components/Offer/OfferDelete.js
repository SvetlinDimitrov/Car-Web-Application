import { useParams, useNavigate } from "react-router-dom";
import { useContext, useEffect } from "react";

import { deleteOffer } from "../../utils/OfferService";

import { AuthContext } from "../../contexts/UserAuth";

const OfferDelete = () => {
  const { id } = useParams();
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    async function deleteData() {
      try {
        await deleteOffer(id, user);
        navigate('/offers');
      } catch (error) {
        //TODO:catch the error
      }
    }

    deleteData();
  }, [id , user, navigate]);
};

export default OfferDelete;
