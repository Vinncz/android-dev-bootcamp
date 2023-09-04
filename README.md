# SMG (Simple Mobile Gallery)

### Snapshots

Supports both dark and lightmode:

// auth
![s](https://i.ibb.co/X8wHdSL/Screenshot-20230830-002545-Android-Dev-Bootcamp.png)
![s](https://i.ibb.co/dcJSPdf/Screenshot-20230830-002601-Android-Dev-Bootcamp.png)

// home
![s](https://i.ibb.co/5vb2gBP/Screenshot-20230904-190038-Android-Dev-Bootcamp.png)

// details
![s](https://i.ibb.co/cQNn90b/Screenshot-20230904-190625-Android-Dev-Bootcamp.png)

// profile
![s](https://i.ibb.co/N1tBJxg/Screenshot-20230904-190115-Android-Dev-Bootcamp.png)

### (!) Limitations

This application can only run on devices that are running Android 11 or higher.

### What is it?

This application functions like a gallery, on where you can see a collection of images that are retrieved from Google Firebase's Realtime Database.

### Why make it?

This application was made to fulfill the requirements for completing BNCC's Android Development Bootcamp that was carried out from August 7th until September 7th, 2023.

### Setting up the Firebase Realtime Database

Due to security concerns, I cannot push the google-services.json file to Github for everyone to see. Instead, you may copy and paste the following set of data into your database to simulate the dev env that I am working with:
```json
{
  "data": {
    "images": [
    {
        "desc": "Taken at Zoo Prague, U Trojského zámku, Prag 7, Tschechien; using Canon, EOS R5",
        "name": "Polar Bear of the Prague Zoo",
        "path": "https://images.unsplash.com/photo-1682193965136-c8650b543426?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=480&h=480&q=80"
      },
      {
        "desc": "Taken using iPhone 12 Pro Max",
        "name": "Persian Cat (House Cat)",
        "path": "https://plus.unsplash.com/premium_photo-1673641895483-2119e6d9fd75?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=480&h=480&q=80"
      },
      {
        "desc": "Taken using SONY, ILCE-7RM2",
        "name": "Scarlet Macaw",
        "path": "https://images.unsplash.com/photo-1692019575434-875dbfaeb023?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=480&h=480&q=80"
      },
      {
        "desc": "Taken at Borneo Orangutan Conservation Center; using SONY, ILCE-7RM2",
        "name": "An Infant Orangutan",
        "path": "https://images.unsplash.com/photo-1690971284880-fe1a22fbe361?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=480&h=480&q=80"
      },
      {
        "desc": "A photo of a good boi taken using Canon, EOS 6D",
        "name": "Golden Retriever Looking Over a Canyon",
        "path": "https://images.unsplash.com/photo-1612774412771-005ed8e861d2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=480&h=480&q=80"
      }
    ],
    "sample_images": [
      {
        "desc": "A randomly generated template picture.",
        "name": "Template Picture 200x200",
        "path": "https://picsum.photos/200/200"
      },
      {
        "desc": "A randomly generated template picture.",
        "name": "Template Picture 300x300",
        "path": "https://picsum.photos/300/300"
      },
      {
        "desc": "A randomly generated template picture.",
        "name": "Template Picture 400x400",
        "path": "https://picsum.photos/400/400"
      },
      {
        "desc": "A randomly generated template picture.",
        "name": "Template Picture 500x500",
        "path": "https://picsum.photos/500/500"
      },
      {
        "desc": "A randomly generated template picture.",
        "name": "Template Picture 600x600",
        "path": "https://picsum.photos/600/600"
      }
    ]
  }
}
```
