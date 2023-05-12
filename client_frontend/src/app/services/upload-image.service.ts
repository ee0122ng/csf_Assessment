import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GET_ARCHIVE_URL, GET_BUNDLES_URL, UPLOAD_URL } from '../constants';
import { lastValueFrom } from 'rxjs';
import { Archive } from '../model/archive';

@Injectable({
  providedIn: 'root'
})
export class UploadImageService {

  constructor(private http: HttpClient) { }

  uploadImage(formData : FormData) : Promise<any> {

    return lastValueFrom(this.http.post<string>(UPLOAD_URL, formData))
  }

  getArchiveDocument(bundleId: string) : Promise<any> {

    return lastValueFrom(this.http.get<string>(GET_ARCHIVE_URL + '/' + bundleId));

  }

  retrieveBundles() {
    return lastValueFrom(this.http.get<any>(GET_BUNDLES_URL))
  }
}
