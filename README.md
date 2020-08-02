# Object pool pattern implementation

## These charts were obtained when the main () method was run, the main () method in the Main class is a small single-threaded document.

### Run in one main thread.

ObjectPool use two lists and synchronized methods.

Common information
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v1_common_information.png?raw=true)

Direct Buffer
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v1_direct_buffer.png?raw=true)


ObjectPoolV2 use ConcurrentHashMap.

Common information
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v2_common_information.png?raw=true)

Direct Buffer
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v2_direct_buffer.png?raw=true)


Multithread runing.

ObjectPool use two lists and synchronized methods.

Common information
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v1_multithread_common_information.png?raw=true)

Direct Buffer
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v1_multithread_direct_buffer.png?raw=true)


ObjectPoolV2 use ConcurrentHashMap.

Common information
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v2_multithread_common_information.png?raw=true)

Direct Buffer
![alt text](https://github.com/miramax777/ObjectPooling/blob/master/Object_pool_v2_multithread_direct_buffer.png?raw=true)
