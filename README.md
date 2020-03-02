# react-native-get-music-files

## Getting started

`$ npm install react-native-get-music-files --save`

### Mostly automatic installation

`$ react-native link react-native-get-music-files`

## Usage

### Imports

```javascript

import MusicFiles, {Constants, CoverImage} from 'react-native-get-music-files';

```

### Methods


```javascript 

search = searchParam => {
      MusicFiles.search({
        searchParam,
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

getAll = () => {
      MusicFiles.getAll({
        cover: true,
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

### Components


```javascript

<CoverImage
          src={
            'pathToSong'
          }
          width={120}
          height={120}
        />
 ```

 ## option objects

``` javaScript
GetAllOptions {
    cover: bool,
    coverFolder: string,
    minimumSongDuration: int,
    batchSize: int,
    batchNumber: int,
    sortBy: Constants.SortB,
    sortOrder: Constants.SortOrde,
}

GetSongsByPathOptions {
    cover: bool,
    coverFolder: string,
    path: string,
}

SearchOptions {
    searchParam: string,
    batchSize: int,
    batchNumber: int,
    sortBy: Constants.SortB,
    sortOrder: Constants.SortOrde,
}
```