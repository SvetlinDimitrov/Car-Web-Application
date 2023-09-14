import { Routes, Route } from "react-router-dom";

import UserAuthProvider from "./contexts/UserAuth";
import UserAuthGuard from "./contexts/UserAuthGuard";

import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Home from "./components/Home/Home";
import Login from "./components/Login/Login";
import Logout from "./components/Logout/Logout";
import Register from "./components/Register/Register";
import Model from "./components/Model/Model";
import ModelDetails from "./components/Model/ModelDetails";
import OfferDetails from "./components/Offer/OfferDetails";
import OfferAdd from "./components/Offer/OfferAdd";
import Offer from "./components/Offer/Offer";
import OfferUpdate from "./components/Offer/OfferUpdate";
import OfferDelete from "./components/Offer/OfferDelete";
import BrandChange from "./components/Brand/BrandChange";
import BrandDelete from "./components/Brand/BrandDelete";
import Error from "./components/Error/Error";
import Brand from "./components/Brand/Brand";
import BrandAdd from "./components/Brand/BrandAdd";
import ModelAdd from "./components/Model/ModelAdd";
import ModelDelete from "./components/Model/ModelDelete";
import ModelUpdate from "./components/Model/ModelUpdate";
import ErrorBoundary from "./ErrorBoundary";

function App() {
  return (
    <>
      <ErrorBoundary>
        <UserAuthProvider>
          <Header />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="*" element={<Error />} />
            <Route element={<UserAuthGuard />}>
              <Route path="/logout" element={<Logout />} />
              <Route path="/models">
                <Route index element={<Model />} />
                <Route path="details/:id" element={<ModelDetails />} />
                <Route path="add" element={<ModelAdd />} />
                <Route path="delete/:id" element={<ModelDelete />} />
                <Route path="update/:id" element={<ModelUpdate />} />
              </Route>
              <Route path="/brands">
                <Route index element={<Brand />} />
                <Route path="add" element={<BrandAdd />} />
                <Route path="update/:id" element={<BrandChange />} />
                <Route path="delete/:id" element={<BrandDelete />} />
              </Route>
              <Route path="/offers">
                <Route index element={<Offer />} />
                <Route path="add" element={<OfferAdd />} />
                <Route path="update/:id" element={<OfferUpdate />} />
                <Route path="details/:id" element={<OfferDetails />} />
                <Route path="delete/:id" element={<OfferDelete />} />
              </Route>
            </Route>
          </Routes>
          <Footer />
        </UserAuthProvider>
      </ErrorBoundary>
    </>
  );
}

export default App;
