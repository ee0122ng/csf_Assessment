import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UPLOAD_URL } from '../constants';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadImageService {

  constructor(private http: HttpClient) { }

  uploadImage(formData : FormData) : Promise<any> {

    return lastValueFrom(this.http.post<string>(UPLOAD_URL, formData))
  }
}
