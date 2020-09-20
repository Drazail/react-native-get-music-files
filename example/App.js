/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {
  StyleSheet,
  Text,
  View,
  PermissionsAndroid,
  Button,
  TextInput,
  ScrollView,
  FlatList,
} from 'react-native';
import MusicFiles, {Constants, CoverImage} from 'react-native-get-music-files';

const Item = () => (
  <CoverImage
    source={
      '/storage/emulated/0/Download/03 - Nocturne No 2 in E flat major.mp3'
    }
    width={20}
    height={20}
    style={{borderRadius: 100}}
  />
);
const DATA = Array.from({length: 1000}).map((v, i) => ({
  id: i,
}));

const renderItem = ({item}) => <Item title={item.title} />;

export default class App extends Component<Props> {
  constructor() {
    super();

    this.requestPermission = async () => {
      try {
        const granted = await PermissionsAndroid.requestMultiple(
          [
            PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
            PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
          ],
          {
            title: 'Permission',
            message: 'Storage access is requiered',
            buttonPositive: 'OK',
          },
        );
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          alert('You can use the package');
        } else {
          this.requestPermission();
        }
      } catch (err) {
        console.warn(err);
      }
    };

    this.search = searchParam => {
      MusicFiles.search({
        searchParam,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Title,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er.message)));
    };

    this.getAll = () => {
      MusicFiles.getAll({
        cover: true,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Title,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.getArtists = () => {
      MusicFiles.getArtists({
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.getAlbums = searchParam => {
      MusicFiles.getAlbums({
        artist: searchParam,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.getSongs = (album, artist) => {
      MusicFiles.getSongs({
        artist: artist,
        album: album,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Artist,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.getSongByPath = () => {
      MusicFiles.getSongsByPath({
        cover: true,
        coverFolder: '/storage/emulated/0/Download/Covers',
        path: '/storage/emulated/0/Download/Help Me.mp3',
      })
        .then(f => {
          this.setState({...this.state, search: f});
        })
        .catch(er => console.log(JSON.stringify(er.message)));
    };

    this.state = {
      searchParam: '',
      getSongsSearchParamArtist: null,
      getSongsSearchParamAlbum: null,
      search: [],
    };
  }

  componentDidMount() {
    this.requestPermission();
  }

  render() {
    return (
      <View style={styles.container}>
        <ScrollView style={styles.scrollVIew}>
          <Text />
          <FlatList
            initialNumToRender={10}
            data={DATA}
            renderItem={renderItem}
            keyExtractor={item => item.id}
          />
          <Text />
          <TextInput
            placeholder="search param"
            onChangeText={v => this.setState({...this.state, searchParam: v})}
            style={styles.input}
          />

          <Text />
          <TextInput
            placeholder="getSongs search param: Artist"
            onChangeText={v =>
              this.setState({...this.state, getSongsSearchParamArtist: v})
            }
            style={styles.input}
          />
          <Text />
          <TextInput
            placeholder="getSongs search param: Album"
            onChangeText={v =>
              this.setState({...this.state, getSongsSearchParamAlbum: v})
            }
            style={styles.input}
          />
          <Text />
          <Button
            title="search"
            onPress={() => this.search(this.state.searchParam)}
          />

          <Text />
          <Button title="getall" onPress={() => this.getAll()} />

          <Text />
          <Button title="getArtists" onPress={() => this.getArtists()} />

          <Text />
          <Button
            title="getAlbums"
            onPress={() => this.getAlbums(this.state.searchParam)}
          />

          <Text />
          <Button
            title="getSongs"
            onPress={() =>
              this.getSongs(
                this.state.getSongsSearchParamAlbum,
                this.state.getSongsSearchParamArtist,
              )
            }
          />

          <Text style={styles.instructions}>
            results : {JSON.stringify(this.state.search)}
          </Text>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    alignContent: 'center',
    backgroundColor: '#F5FCFF',
  },
  scrollVIew: {
    width: '80%',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
    alignSelf: 'center',
    justifyContent: 'center',
  },
  input: {
    borderColor: 'gray',
    borderWidth: 2,
    width: '100%',
    height: 40,
    textAlign: 'center',
    alignSelf: 'center',
    justifyContent: 'center',
  },
});
