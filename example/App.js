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
} from 'react-native';
import MusicFiles, {Constants, CoverImage} from 'react-native-get-music-files';

type Props = {};
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
        cover: false,
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
          <CoverImage
            source={'/storage/emulated/0/Download/Hurt - Johnny Cash.mp3'}
            placeHolder={
              'https://cdn2.iconfinder.com/data/icons/Qetto___icons_by_ampeross-d4njobq/256/library-music.png'
            }
            width={120}
            height={120}
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
