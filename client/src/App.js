import { Routes, Route } from "react-router-dom";

import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Model from "./components/Model/Model";
import OfferDetails from "./components/Offer/OfferDetails";
import OfferAdd from "./components/Offer/OfferAdd";
import Offer from "./components/Offer/Offer";
import OfferUpdate from "./components/Offer/OfferUpdate";

import UserAuthProvider from "./contexts/UserAuth";
import OfferProvider from "./contexts/OfferContext";

function App() {
  return (
    <>
      <UserAuthProvider>
        <OfferProvider>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/models" element={<Model />} />
            <Route path="/offers" element={<Offer />} />
            <Route path="/offers/add" element={<OfferAdd />} />
            <Route path="/offers/update" element={<OfferUpdate />} />
            <Route path="/offers/details/:id" element={<OfferDetails />} />
          </Routes>
          <Footer />
        </OfferProvider>
      </UserAuthProvider>
    </>
  );
}

export default App;
