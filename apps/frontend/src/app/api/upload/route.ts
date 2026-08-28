import { cloudinary } from "@/lib/cloudinary";
import { UploadApiResponse } from "cloudinary";
import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
  const formData = await request.formData();
  const file = formData.get("file") as File | null;

  if (!file) {
    return NextResponse.json(
      { error: "No se envió ningún archivo" },
      { status: 400 },
    );
  }

  const bytes = await file.arrayBuffer();
  const buffer = Buffer.from(bytes);
  const extension = file.name.split(".").pop() || "";

  const result: UploadApiResponse = await new Promise((resolve, reject) => {
    cloudinary.uploader
      .upload_stream(
        { resource_type: "raw", access_mode: "public", format: extension },
        (error, result) => {
          if (error || !result) reject(error);
          else resolve(result);
        },
      )
      .end(buffer);
  });

  return NextResponse.json({
    url: result.secure_url,
    name: result.original_filename,
    type: extension.toUpperCase(),
  });
}
