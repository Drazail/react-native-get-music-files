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
        cover: true,
        batchSize: 0,
        batchNumber: 0,
        sortBy: Constants.SortBy.Title,
        sortOrder: Constants.SortOrder.Ascending,
      })
        .then(f => {
          this.setState({...this.state, getAll: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.getSongByPath = () => {
      MusicFiles.getSongsByPath({
        cover: true,
        coverFolder: '/storage/emulated/0/Download/Covers',
        path: '/storage/emulated/0/Download/01 - Moonromanticism.mp3',
      })
        .then(f => {
          this.setState({...this.state, getAll: f});
        })
        .catch(er => console.log(JSON.stringify(er)));
    };

    this.state = {
      getAlbumsInput: '',
      getSongsInput: {},
      searchParam: '',
      tracks: [],
      artists: [],
      albums: [],
      songs: [],
      search: [],
      getAll: [],
    };
  }

  componentDidMount() {
    this.requestPermission();
  }

  render() {
    return (
      <View style={styles.container}>
        <CoverImage
          src={
            '/storage/emulated/0/Music/Ahangify/Alireza Pouya - Kotah Bia.mp3'
          }
          width={120}
          height={120}
          backgroundColor="red"
        />
        <TextInput
          placeholder="search"
          onChangeText={v => this.setState({...this.state, searchParam: v})}
        />
        <Button
          title="search"
          onPress={() => this.search(this.state.searchParam)}
        />
        <ScrollView style={{height: 100, width: '100%'}}>
          <Text style={styles.instructions}>
            results : {JSON.stringify(this.state.search)}
          </Text>
        </ScrollView>

        <Text>getAll</Text>
        <Button title="search" onPress={() => this.getSongByPath()} />
        <ScrollView style={{height: 100, width: '100%'}}>
          <Text style={styles.instructions}>
            results : {JSON.stringify(this.state.getAll)}
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
    backgroundColor: '#F5FCFF',
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
  },
});
