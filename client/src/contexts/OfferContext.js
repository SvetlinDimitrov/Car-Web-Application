import { createContext, useState} from "react";

export const OfferContext = createContext();

const OfferProvider = ({ children }) => {
  const [offers, setOffers] = useState([]);
  const [fetch , setFetch] = useState(false);
    
  const initOffers = (data) => {
    
    if(!fetch){
      setOffers(data);
      setFetch(true);
    }
  };

  const offerData = { offers , initOffers ,setFetch};
  return (
    <OfferContext.Provider value={offerData}>{children}</OfferContext.Provider>
  );
};
export default OfferProvider;
