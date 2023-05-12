import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadImageService } from '../services/upload-image.service';

@Component({
  selector: 'app-view1',
  templateUrl: './view1.component.html',
  styleUrls: ['./view1.component.css']
})
export class View1Component implements OnInit {

  @ViewChild('image')
  imageFile !: ElementRef

  form !: FormGroup;
  imageCaptured : boolean = false;

  $upload !: Promise<any>;
  returnSuccess !: boolean;
  message !: string;
  bundleId !: string;

  constructor(private fb: FormBuilder, private router: Router, private uploadImgSvc: UploadImageService) {}

  ngOnInit(): void {
    this.form = this.createForm()

    this.uploadImgSvc
  }

  createForm() : FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      title: this.fb.control<string>('', [Validators.required]),
      comments: this.fb.control<string>('')
    })
  }

  submitForm() {

    const formData : FormData = new FormData();

    // capture today date for archive purpose
    const uploadDate : string = new Date().toLocaleString();

    formData.set('image', this.imageFile.nativeElement.files[0])
    formData.set('name', this.form.get('name')?.value)
    formData.set('title', this.form.get('title')?.value)
    formData.set('comments', this.form.get('comments')?.value)
    formData.set('uploadDate', uploadDate)

    this.uploadImgSvc.uploadImage(formData)
      .then( (p:any) => {
        this.returnSuccess = true
        this.bundleId = p["bundleId"]
        console.info(">>> message receieved: ", this.bundleId)
        this.router.navigate(['/display', this.bundleId])
      })
      .catch((p:any) => {
        this.returnSuccess = false;
        this.message = p["error"]["error"]
      });

  }

  // create a event listener to validate the file field
  onFileUpload($event: any) {
    let file = $event.target.files[0]

    if (file) {
      this.imageCaptured = true
    }
  }

  isFormValid() {

    return this.form.valid && this.imageCaptured
  }

}
