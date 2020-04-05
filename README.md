# react-native-get-music-files

## Instalation

`$ npm i react-native-get-music-files-v3dev-test`

## Imports

```javascript

import MusicFiles, {Constants, CoverImage} from 'react-native-get-music-files-v3dev-test';

```
## Constants

```javaScript
{
  SortBy: {
    Artist: 'ARTIST',
    Album: 'ALBUM',
    Title: 'TITLE',
  },
  SortOrder: {
    Ascending: 'ASC',
    Descending: 'DESC',
  },
}

```

### example

```javascript

import {Constants} from 'react-native-get-music-files-v3dev-test';

const sortBy = Constants.SortBy.Title,
const sortOrder = Constants.SortOrder.Ascending,

```


## Methods


### search

#### options

all attributes of the options object are optional

```
{
  searchParam?: string,
  batchNumber?: string,
  batchSize?: string,
  sortBy?: string,
  sortOrder?: string,
}
```
#### returns

```
{
  "results":{
    id: number,
    path: string,
    duration: number,
    album: string,
    artist: string,
    title: string
  },
  "length" : number
}
```

#### example
```javascript 

search = searchParam => {
      MusicFiles.search({
        "searchParam",
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Title,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          console.log(f);
        })
        .catch(er => console.log(e));
    };
```

### getAll

#### options

all attributes are optional

```
{
  cover?: boolean,
  coverFolder?: string,
  minimumSongDuration?: number,
  batchSize?: number,
  batchNumber?: number,
  sortBy?: string,
  sortOrder?: string,
}
```
#### returns

```
{
  "results":{
    id: number,
    path: string,
    cover: string,
    duration: number,
    album: string,
    artist: string,
    title: string
    
  },
  "length" : number
}
```

#### example

```javascript
getAll = () => {
      MusicFiles.getAll({
        cover: false,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Title,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          console.log(f);
        })
        .catch(er => console.log(e));
    };
```

### getSongByPath

#### options

all attributes are optional

```
{
  cover?: boolean,
  coverFolder?: string,
  path?: string,
}
```
#### returns

```
{
  id: number,
  path: string,
  cover: string,
  duration: number,
  album: string,
  artist: string,
  title: string
}
```

#### example

```javascript
getSongByPath = () => {
      MusicFiles.getSongsByPath({
        cover: true,
        coverFolder: 'pathToCoverFolder',
        path: 'pathToSong',
      })
        .then(f => {
          console.log(f);
        })
        .catch(er => console.log(e));
    };

```

### getSongByPaths

scans a directory

#### options

all attributes are optional

```
{
  cover?: boolean,
  path?: string,
}
```
#### returns

```
{
  id: number,
  path: string,
  cover: string,
  duration: number,
  album: string,
  artist: string,
  title: string
}[]
```

#### example

```javascript
getSongByPaths = () => {
      MusicFiles.getSongsByPaths({
        cover: true,
        coverFolder: 'pathToCoverFolder',
        path: 'pathToDirectory',
      })
        .then(f => {
          console.log(f);
        })
        .catch(er => console.log(e));
    };

```

### getArtists

#### options

all attributes are optional

```
{
batchSize?: number,
batchNumber?: number,
sortBy?: string,
sortOrder?: string,
}
```
#### returns

```
{
  artist: string,
  numberOfAlbums: number,
  numberOfSongs: number,
}
```

#### example

```javascript
getArtists = () => {
      MusicFiles.getArtists({
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
           console.log(f);
        })
        .catch(er => console.log(e));
    };
```

### getAlbums

#### options

all attributes are optional

```
{
artist?: string,
batchSize?: number,
batchNumber?: number,
sortBy?: string,
sortOrder?: string,
}
```
#### returns

```
{
  id: string,
  album: string,
  artist: string,
  numberOfSongs: number,
  firstYear: number,
  lastYear: number,
}
```

#### example

```javascript
getAlbums = () => {
       MusicFiles.getAlbums({
        artist: "searchParam",
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
           console.log(f);
        })
        .catch(er => console.log(e));
    };
```

### getSongs

#### options

all attributes are optional

```
{
artist?: string,
album?: string,
batchSize?: number,
batchNumber?: number,
sortBy?: string,
sortOrder?: string,
}
```
#### returns

```
{
  id: string,
  title: string,
  album: string,
  artist: string,
  duration: number,
  path: string,
}
```

#### example

```javascript
getSongs = () => {
       MusicFiles.getSongs({
        artist: "artist",
        album: "album",
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
           console.log(f);
        })
        .catch(er => console.log(e));
    };
```



### Components

use this component instead of `cover: true` for better performance.

inherits from Image.

```javascript

<CoverImage
          src={
            'pathToSong'
          }
          width={120}
          height={120}
        />
 ```

## Example

https://github.com/Drazail/react-native-get-music-files/blob/v3dev/example/App.js