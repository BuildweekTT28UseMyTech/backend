## API LINK: https://rheact-usemytech.herokuapp.com/

## AUTHENTICATION

Your login request should look like this:
```
axios.post('https://rheact-usemytech.herokuapp.com/', `grant_type=password&username=${credentials.username}&password=${credentials.password}`, {
      headers: {
        // btoa is converting our client id/client secret into base64
        Authorization: `Basic ${btoa('XXXXXXX:XXXXXXXX')}`,
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
```
For registering a user, the format is:

    {
       "username": "theuser",
       "password": "secretive",
       "primaryemail": "name@domain.com",
       "type": "owner"
    }
| Type | Endpoint       | Function                                   | Request Body                     |
|------|----------------|--------------------------------------------|----------------------------------|
| POST | /createnewuser | Creates new user and generates auth token. | username, password, primaryemail, type |
| POST | /login         | Logs in user and returns auth token.       | username, password               |
| GET  | /logout        | Logs out user from database.               |                                  |

**Type** refers to whether a user is an *owner* or *renter*. It is important that the string is exactly "owner" or "renter" (so completely lowercase and correct spelling).

## USER

    {
        "userid": 4,
        "username": "admin",
        "primaryemail": "admin@lambdaschool.local",
        "type": "owner",
        "useremails": [],
        "owneditems": [
            {
                "owneditem": {
                    "itemid": 15,
                    "itemname": "hellohellob",
                    "price": 300.0
                }
            }
        ],
        "renteditems": [
            {
                "renteditem": {
                    "itemid": 15,
                    "itemname": "hellohellob",
                    "price": 300.0
                }
            }
        ]
    }

Pay close attention to the structure of this object.
As you can see, **owneditems** is an array which contains a singular object, **owneditem** which contains the information of the item.


| Type | Endpoint          | Function                                                 | Request Body |
|------|-------------------|----------------------------------------------------------|--------------|
| GET  | /users/users      | Returns an array with all users.                         |              |
| GET  | /users/user/{id}  | Returns the user at given id.                            |              |
| GET  | /user/getuserinfo | Returns the user object of the currently logged in user. |              |

 

## ITEM

The item object's structure is as below.

    {
        "price": 300.0,
        "itemname": "hellohellob",
        "owner": {
            "userid": 4,
            "username": "admin",
            "primaryemail": "admin@lambdaschool.local"
        },
        "renter": {
            "userid": 5,
            "username": "renter",
            "primaryemail": "renter@lambdaschool.local"
        },
        "itemid": 18
    }
If there is no renter for the item yet, it will be **null.**

Although the item contains quite a bit of information, the only values it can accept during creation are *itemname* and *price.*

| Type | Endpoint           | Function                                                                                         | Request Body       |
|------|--------------------|--------------------------------------------------------------------------------------------------|--------------------|
| GET  | /items/items       | Returns all items.                                                                               |                    |
| GET  | /items/item/{id}   | Returns item at given id.                                                                        |                    |
| POST | /items/item        | Creates new item with logged in user as owner.                                                   | itemname and price |
| PUT  | /items/rent/{id}   | The currently logged in user becomes the renter of the item at given id.                         |                    |
| PUT  | /items/unrent/{id} | If the currently logged in user is renting the item at the given id, removes them as the renter. |                    |
| DELETE| /items/item/{id} | Deletes the item at the given id. |                    |
