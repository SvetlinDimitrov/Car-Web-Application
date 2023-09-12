import { useEffect, useState } from "react";
import {getAllModel} from '../../utils/ModelService';

const Model = () => {
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
      <h2 className="text-center text-white m-3">All Brands</h2>
      <div className="row mb-4 d-flex justify-content-around">
        <div className="brand-section col-md-5 mr-auto d-flex flex-column">
          <div>
            <table>
              <thead>
                <tr>
                  <th>No</th>
                  <th>Name</th>
                  <th>Category</th>
                  <th>Start Year</th>
                  <th>Picture</th>
                </tr>
              </thead>
              <tbody>
                {brands.map((b , index) => (
                  <tr key={b.id}>
                    <th>{index}</th>
                    <th>{b.name}</th>
                    <th>{b.category}</th>
                    <th>{b.created}</th>
                    <th>
                      <img alt="" src={b.imageUrl} className="img-thumbnail rounded" />
                    </th>
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
