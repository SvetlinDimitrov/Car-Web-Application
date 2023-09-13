import { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";

import { AuthContext } from "../../contexts/UserAuth";

import { getAllBrands } from "../../utils/BrandService";

const Brand = () => {
  const { user } = useContext(AuthContext);
  const [brands, setBrands] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const data = await getAllBrands();
      setBrands(data);
    };
    fetchData();
  }, []);

  return (
    <div className="container">
      <h2 className="text-center text-white m-3">All Brands</h2>
      <div className="row mb-4 d-flex justify-content-around">
        <div className="brand-section col-md-5 mr-auto d-flex flex-column w-50">
          <div>
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Created</th>
                  <th>Modified</th>
                  <th>Available offers</th>
                </tr>
              </thead>
              <tbody>
                {brands.map((b, index) => (
                  <tr key={b.id}>
                    <th>{index}</th>
                    <th>{b.name}</th>
                    <th>{b.created}</th>
                    <th>{b.modified}</th>
                    <th>{b.modelList.length}</th>
                    {user.authorities.some((r) => r === "ROLE_ADMIN") && (
                      <>
                        <th>
                          <button type="button" className="btn btn-primary">
                            <Link className="nav-link" to={`/brands/update/${b.id}`}>
                              Change
                            </Link>
                          </button>
                        </th>
                        <th>
                          <button type="button" className="btn btn-danger">
                          <Link className="nav-link" to={`/brands/delete/${b.id}`}>
                              Delete
                            </Link>
                          </button>
                        </th>
                      </>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Brand;
