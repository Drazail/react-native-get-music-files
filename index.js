import { NativeModules, requireNativeComponent } from 'react-native';


const { GetMusicFiles } = NativeModules;

export default GetMusicFiles;

export const Constants = {
  SortBy: {
    Artist: 'ARTIST',
    Album: 'ALBUM',
    Title: 'TITLE',
  },
  SortOrder: {
    Ascending: 'ASC',
    Descending: 'DESC',
  },
};

export const CoverImage = requireNativeComponent('RCTCoverImageView');
