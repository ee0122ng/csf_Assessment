import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UploadImageService } from '../services/upload-image.service';
import { Archive } from '../model/archive';

@Component({
  selector: 'app-view0',
  templateUrl: './view0.component.html',
  styleUrls: ['./view0.component.css']
})
export class View0Component implements OnInit {

  uploads : Archive[] = [];

  constructor(private router: Router, private uploadImgSvc: UploadImageService) {}

  ngOnInit(): void {
      this.uploadImgSvc.retrieveBundles()
      .then((p:any[]) => {
        return p.map((a:any) => {
          return {
            bundleId: a["bundleId"],
            title: a["title"],
            name: a["name"],
            upload: new Date(a["uploadDate"]),
            comments: a["comments"],
            urls: a["urls"]
          } as Archive
        })
      })
      .then((p: Archive[]) => {
        this.uploads = p
      });
  }
  

  uploadImage() {
    this.router.navigate(['upload'])
  }

  retrieveArchive(bundleId: string) {
    this.router.navigate(['/display', bundleId])
  }

}
