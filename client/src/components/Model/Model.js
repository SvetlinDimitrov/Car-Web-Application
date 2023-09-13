import { Link } from 'react-router-dom';
import { useEffect, useState , useContext } from "react";

import { AuthContext } from '../../contexts/UserAuth';
import { getAllModel } from '../../utils/ModelService';

const Model = () => {
  const { user } = useContext(AuthContext);
  const [brands, setBrands] = useState([]);

  useEffect(() => {
    const getData = async () => {
      const data = await getAllModel();
      setBrands(data);
    } 
    getData();
  }, []);

  return (
    <div className="container">
      <h2 className="text-center text-white m-3">All Models</h2>
      <div className="row mb-4 d-flex justify-content-around">
        <div className="brand-section col-md-5 mr-auto d-flex flex-column w-75">
          <div>
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Category</th>
                  <th>Start Year</th>
                  <th>Picture</th>
                  <th>More Info</th>
                </tr>
              </thead>
              <tbody>
                {brands.map((m , index) => (
                  <tr key={m.id}>
                    <th>{index}</th>
                    <th>{m.name}</th>
                    <th>{m.category}</th>
                    <th>{m.created}</th>
                    <th>
                      <img alt="" src={m.imageUrl} className="img-thumbnail rounded" />
                    </th>
                    
                    {user.authorities.some((r) => r === "ROLE_ADMIN") && (
                      <>
                        <th>
                          
                            <Link className="nav-link" to={`/models/details/${m.id}`}>
                              View Details
                            </Link>
                      
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

export default Model;
