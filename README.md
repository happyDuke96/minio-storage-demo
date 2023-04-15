# Minio File Storage

The service written with Spring Boot

- ##### Install minio service
* **Minio server windows** [download](https://min.io/download#/windows) 
* **Minio client windows (optional)** [download](https://min.io/download#/windows) 
* **Minio documentation** [read more](https://min.io/docs/minio/windows/index.html)


You can set system environment variable <br/>
- Invoke-WebRequest -Uri "https://dl.min.io/server/minio/release/windows-amd64/minio.exe" -OutFile "C:\minio.exe" <br/>
- setParam MINIO_ROOT_USER admin <br/>
- setParam MINIO_ROOT_PASSWORD password <br/>
- C:\minio.exe server F:\Data --console-address ":9090" <br/>


If you install the binary, you can go into the installed Minio folder and run this command
- .\minio.exe server C:\minio --console-address :9090
- * username : minioadmin
- * password : minioadmin


* Note : For save to minio bucket you need to change port on application.yaml file to 9000 this port for port address
* --address for API port 9000
* --console-address for UI port 9090



