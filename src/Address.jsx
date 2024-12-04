import React, { useState } from "react";
import PlacesAutocomplete from "react-places-autocomplete";
import {
  geocodeByAddress,
  geocodeByPlaceId,
  getLatLng,
} from "react-places-autocomplete";

function AddressForm({ address, setAddress, setCoordinates }) {
  const handleSelect = async (value) => {
    try {
      const results = await geocodeByAddress(value);
      const latLng = await getLatLng(results[0]);
      setAddress(value);
      setCoordinates(latLng);
    } catch (error) {
      console.error("Error:", error);
      alert("Failed to fetch address details.");
    }
  };

  return (
    <PlacesAutocomplete
      value={address}
      onChange={setAddress}
      onSelect={handleSelect}
    >
      {({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
        <div>
          <input
            {...getInputProps({
              placeholder: "Type address",
              className: "location-search-input",
            })}
          />
          <div>
            {loading && <div>Loading...</div>}
            {suggestions.map((suggestion, index) => {
              const style = suggestion.active
                ? { backgroundColor: "#41b6e6", cursor: "pointer" }
                : { backgroundColor: "#BBBBBB", cursor: "pointer" };
              return (
                <div
                  {...getSuggestionItemProps(suggestion, { style })}
                  key={index}
                >
                  {suggestion.description}
                </div>
              );
            })}
          </div>
        </div>
      )}
    </PlacesAutocomplete>
  );
}

export { AddressForm };
