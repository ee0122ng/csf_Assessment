import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UploadImageService } from '../services/upload-image.service';
import { Archive } from '../model/archive';

@Component({
  selector: 'app-view2',
  templateUrl: './view2.component.html',
  styleUrls: ['./view2.component.css']
})
export class View2Component implements OnInit {

  result !: Archive;
  hasError : boolean = false;
  error !: string;
  archive$ !: Promise<any>;

  constructor(private activatedRoute: ActivatedRoute, private uploadImgSvc: UploadImageService) {}

  ngOnInit(): void {
      const bundleId : string = this.activatedRoute.snapshot.params['bundleId']
      // console.info('>>> bundleId: ', bundleId)
      this.archive$ = this.uploadImgSvc.getArchiveDocument(bundleId);
      this.archive$
        .then((p:any) => {
          console.info(">>> upload date returned: ", p["uploadDate"])
          return {
            bundleId: p["bundleId"],
            title: p["title"],
            name: p["name"],
            upload: new Date(p["uploadDate"]),
            comments: p["comments"],
            urls: p["urls"]
          } as Archive
        })
        .then((p:Archive) => {
          this.result = p
        })
        .catch(
          (err) => {
            this.hasError = true
            this.error = err["error"]["error"]
          }
        )

  }

}
