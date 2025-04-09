var john = {
    name: {
        fist: 'j'
    }
}

var emp = {
    name: "Rajesh",
    address: {
        locality: {
            street: "2nd main domlur"
        }
    }
}

function getObjPath(jsObj, path) {

    var str = path.split('.');
    var a = null;
    //str.splice(0, 0, jsObj.toString());
    for (var i = 0; i < str.length; i++) {
        var prop = str[i];

        if (!jsObj || !jsObj.hasOwnProperty(prop)) {
            return null;
        } else {
            jsObj = jsObj[prop];
            a = jsObj;
        }
    }
    return a;
}
//console.log(emp.address.locality.street);
var a = getObjPath(emp, 'address.locality.street');
console.log(a);