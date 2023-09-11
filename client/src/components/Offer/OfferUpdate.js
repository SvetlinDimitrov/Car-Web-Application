const OfferUpdate = () => {
  return (
    <div className="container">
      <h2 className="text-center text-white">Update Offer</h2>
      <form
        method="POST"
        className="main-form mx-auto col-md-8 d-flex flex-column justify-content-center"
      >
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label className="text-center text-white font-weight-bold" htmlFor="model3">
              Model
            </label>
            <select id="model3" className="form-control">
              <option value="">- Select a model -</option>
              <optgroup label="Brand name">
                <option>Model</option>
              </optgroup>
            </select>
            <p className="errors alert alert-danger">Vehicle model is required.</p>
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="price3" className="text-white font-weight-bold">
              Price
            </label>
            <input
              id="price3"
              type="number"
              min="0"
              step="100"
              className="form-control"
              placeholder="Suggested price"
            />
            <p className="errors alert alert-danger">
              Suggested price is required.
            </p>
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label className="text-center text-white font-weight-bold" htmlFor="engine3">
              Engine
            </label>
            <select id="engine3" className="form-control">
              <option value="">- Select engine type -</option>
              <option>Engine type</option>
            </select>
            <p className="errors alert alert-danger">Engine type is required.</p>
          </div>
          <div className="form-group col-md-6 mb-3">
            <label
              className="text-center text-white font-weight-bold"
              htmlFor="transmission3"
            >
              Transmission
            </label>
            <select id="transmission3" className="form-control">
              <option value="">- Select transmission type -</option>
              <option> Transmission type</option>
            </select>
            <p className="errors alert alert-danger">
              Transmission type is required.
            </p>
          </div>
        </div>
        <div className="row">
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="year3" className="text-white font-weight-bold">
              Year
            </label>
            <input
              id="year3"
              type="number"
              min="1900"
              max="2099"
              step="1"
              className="form-control"
              placeholder="Manufacturing year"
            />
            <p className="errors alert alert-danger">
              Manufacturing year is required.
            </p>
          </div>
          <div className="form-group col-md-6 mb-3">
            <label htmlFor="mileage3" className="text-white font-weight-bold">
              Mileage
            </label>
            <input
              id="mileage3"
              type="number"
              min="0"
              max="900000"
              step="1000"
              className="form-control"
              placeholder="Mileage in kilometers"
            />
            <p className="errors alert alert-danger">
              Mileage in kilometers is required.
            </p>
          </div>
        </div>

        <div className="form-group">
          <label className="text-white font-weight-bold" htmlFor="description3">
            Description
          </label>
          <textarea
            id="description3"
            type="textarea"
            className="form-control"
            rows="3"
            placeholder="Description"
          ></textarea>
          <p className="errors alert alert-danger">Description is required.</p>
        </div>
        <div className="form-group">
          <label className="text-white font-weight-bold" htmlFor="imageUrl3">
            Image URL
          </label>
          <input
            id="imageUrl3"
            type="url"
            className="form-control"
            placeholder="Put vehicle image URL here"
          />
          <p className="errors alert alert-danger">
            Vehicle image URL is required.
          </p>
        </div>

        <div className="row">
          <div className="col col-md-4">
            <div className="button-holder d-flex">
              <input
                type="submit"
                className="btn btn-info btn-lg"
                value="Update Offer"
              />
            </div>
          </div>
        </div>
      </form>
    </div>
  );
};

export default OfferUpdate;
