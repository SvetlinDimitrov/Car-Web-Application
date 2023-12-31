import { useContext } from "react";
import { Link } from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";

const Header = () => {
  const { user } = useContext(AuthContext);

  return (
    <nav className="navbar navbar-expand-lg bg-dark navbar-dark fixed-top">
      <Link className="navbar-brand" to="/">
        <img alt="car" src="/img/car.png" className="logo" />
      </Link>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto col-12 justify-content-between">
          {user.id ? (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/brands">
                  All Brands
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/models">
                  All Models
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/offers">
                  All Offers
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/offers/add">
                  Add Offer
                </Link>
              </li>
              {user.authorities.some((r) => r === "ROLE_ADMIN") && (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/brands/add">
                      Add Brand
                    </Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/models/add">
                      Add Model
                    </Link>
                  </li>
                </>
              )}

              <li className="nav-item">
                <div className="form-inline my-2 my-lg-0 border px-3">
                  <div
                    className="logged-user"
                    text={`Welcome ${user.username}!`}
                  >
                    <Link className="nav-link" to="/logout">
                      Logout
                    </Link>
                  </div>
                </div>
              </li>
            </>
          ) : (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/register">
                  Register
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Login
                </Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default Header;
