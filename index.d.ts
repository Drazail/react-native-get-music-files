
import { ImageProps } from "react-native";

// constsnts
export namespace Constants {
    export namespace SortBy {
        export const Artist: string;
        export const Album: String;
        export const Title: String;
    }
    export namespace SortOrder {
        export const Ascending: String;
        export const Descending: String;
    }
}

// components
interface ICoverImageProps extends ImageProps {
    src: string;

}
export const CoverImage: (props: ICoverImageProps) => React.SFC<ICoverImageProps>;

// methods
export default MusicFiles;

namespace MusicFiles {
    export function search(options: {
        searchParam?: string,
        batchNumber?: string,
        batchSize?: string,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: number,
            path: string,
            duration: number,
            album: string,
            artist: string,
            title: string
        }[],
        "length": number
    }>;

    export function getAll(options: {
        cover?: boolean,
        coverFolder?: string,
        minimumSongDuration?: number,
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: number,
            path: string,
            cover?: string,
            duration: number,
            album: string,
            artist: string,
            title: string
        }[],
        "length": number
    }>;

    export function getSongByPath(options: {
        cover?: boolean,
        coverFolder?: string,
        path?: string,
    }): Promise<{

        id: number,
        path: string,
        cover?: string,
        duration: number,
        album: string,
        artist: string,
        title: string

    }>;

    export function getSongByPaths(options: {
        path?: string,
        minFileSize?: number,
        maxFileSize?: number,
        extensionFilter?: string,
    }): Promise<{
        id: number,
        path: string,
        duration: number,
        album: string,
        artist: string,
        title: string
    }[]
    >;

    export function getArtists(options: {
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            artist: string,
            numberOfAlbums: number,
            numberOfSongs: number,
        }[],
        "length": number
    }>;

    export function getAlbums(options: {
        artist?: string,
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: string,
            album: string,
            artist: string,
            numberOfSongs: number,
            firstYear: number,
            lastYear: number,
        }[],
        "length": number
    }>;

    export function getSongs(options: {
        artist?: string,
        album?: string,
        batchSize?: number,
        batchNumber?: number,
        sortBy?: string,
        sortOrder?: string,
    }): Promise<{
        "results": {
            id: string,
            title: string,
            album: string,
            artist: string,
            duration: number,
            path: string,
        }[],
        "length": number
    }>;

}




