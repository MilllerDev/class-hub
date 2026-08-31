import { cloudinary } from "@/lib/cloudinary";
import { UploadApiResponse } from "cloudinary";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  const formData = await request.formData();
  const file = formData.get("avatarUrl") as File;

  const bytes = await file.arrayBuffer();
  const buffer = Buffer.from(bytes);

  const result: UploadApiResponse = await new Promise((resolve, reject) => {
    cloudinary.uploader
      .upload_stream({}, (error, result) => {
        if (error || !result) reject(error);
        else resolve(result);
      })
      .end(buffer);
  });

  return NextResponse.json({ url: result.secure_url });
}
