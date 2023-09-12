import { Routes, Route } from "react-router-dom";

import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import Logout from "./components/Logout/Logout";
import Register from "./components/Register/Register";
import Model from "./components/Model/Model";
import OfferDetails from "./components/Offer/OfferDetails";
import OfferAdd from "./components/Offer/OfferAdd";
import Offer from "./components/Offer/Offer";
import OfferUpdate from "./components/Offer/OfferUpdate";
import OfferDelete from "./components/Offer/OfferDelete";
import BrandChange from "./components/Brand/BrandChange";

import UserAuthProvider from "./contexts/UserAuth";
import Brand from "./components/Brand/Brand";
import BrandAdd from "./components/Brand/BrandAdd";


function App() {
  return (
    <>
      <UserAuthProvider>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/logout" element={<Logout />} />
            <Route path="/models" element={<Model />} />
            <Route path="/brands" element={<Brand/>} />
            <Route path="/brands/add" element={<BrandAdd/>} />
            <Route path="/brands/update/:id" element={<BrandChange/>} />
            <Route path="/offers" element={<Offer />} />
            <Route path="/offers/add" element={<OfferAdd />} />
            <Route path="/offers/update/:id" element={<OfferUpdate />} />
            <Route path="/offers/details/:id" element={<OfferDetails />} />
            <Route path="/offers/delete/:id" element={<OfferDelete />} />
           
          </Routes>
          <Footer />
      </UserAuthProvider>
    </>
  );
}

export default App;
