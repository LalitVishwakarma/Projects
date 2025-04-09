# Assignment for a Javascript Developer

## Assignment Details
* Given a JavaScript object, write a function named `getObjPath(jsObj, path)` to search if the path is present in the object.
    * If the path exist return the result of the path
    * If the path doesn't exist return null.

Below given an example javascript object, your function `getObjPath(emp, 'address.locality.street')` should return "2nd main domlur"

```javascript
Let emp = {
  name: "Rajesh",
  address : {
         locality : {
               street : "2nd main domlur"
          }
    }
}
```